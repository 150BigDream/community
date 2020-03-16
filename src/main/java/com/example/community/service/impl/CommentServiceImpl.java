package com.example.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.community.Exception.CustomizeErrorCode;
import com.example.community.Exception.CustomizeException;
import com.example.community.dto.CommentDTO;
import com.example.community.enums.CommentTypeEnum;
import com.example.community.enums.NotificationStatusEnum;
import com.example.community.enums.NotificationTypeEnum;
import com.example.community.mapper.NotificationMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Comment;
import com.example.community.mapper.CommentMapper;
import com.example.community.model.Notification;
import com.example.community.model.Question;
import com.example.community.model.User;
import com.example.community.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2020-03-13
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionServiceImpl questionService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
        //问题对不上
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        //评论类型对不上
        if (comment.getType()==null||!CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment parentComment = commentMapper.selectById(comment.getParentId());
            if (parentComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            //回复问题
            Question dbQuestion = questionMapper.selectById(comment.getParentId());
            if (dbQuestion==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);

            //增加评论数
            UpdateWrapper<Comment> updateWrapper=new UpdateWrapper<>();
            updateWrapper.setEntity(parentComment).set("comment_count",parentComment.getCommentCount()+1);
            commentService.update(updateWrapper);

            // 创建通知
            createNotify(comment, parentComment.getCommentator(), commentator.getName(), dbQuestion.getTitle(), NotificationTypeEnum.REPLY_COMMENT, dbQuestion.getId());
        }else {

            //回复问题
            Question dbQuestion = questionMapper.selectById(comment.getParentId());
            if (dbQuestion==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //两个sql语句，因为抖动之类的原因有语句可能回执行失败
            // 可以用事务commit，但是在实际开发中，insert是主逻辑，要保证插入，而更新应该用其他的手段去恢复
            commentMapper.insert(comment);
            //增加回复数
            UpdateWrapper<Question> updateWrapper=new UpdateWrapper<>();
            updateWrapper.set("comment_count",dbQuestion.getCommentCount()+1)
            .setEntity(dbQuestion);
            questionService.update(updateWrapper);

            // 创建通知
            createNotify(comment, dbQuestion.getCreator(), commentator.getName(), dbQuestion.getTitle(), NotificationTypeEnum.REPLY_QUESTION, dbQuestion.getId());
        }
    }

    /**
     *
     * @param comment   评论
     * @param receiver  接收到通知的人的id
     * @param notifierName  引起通知的人
     * @param outerTitle    标题
     * @param notificationType  类型
     * @param outerId
     */
    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        //自己评论自己就啥也不做
        if (receiver == comment.getCommentator()) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    /**
     * 列出所有的评论。对问题的评论，对评论的评论
     * @param id 问题主键/评论主键
     * @param typeEnum 是问题类型还是评论类型
     * @return
     */
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum typeEnum) {
        QueryWrapper<Comment> questionQueryWrapper=new QueryWrapper<>();
        questionQueryWrapper.eq("parent_id",id)
                .eq("type", typeEnum.getType())
                .orderByDesc("gmt_create");
        List<Comment> comments = commentMapper.selectList(questionQueryWrapper);
        //没有评论,直接给一个空的arrayList
        if (comments.size()==0){
            return new ArrayList<>();
        }

        //TODO 看一下这相关的内容
        //获取去重的评论人id
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转化为map
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("id",userIds);
        List<User> users = userMapper.selectList(queryWrapper);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment为commentDTO
        List<CommentDTO> CommentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return CommentDTOS;
    }
}
