package com.example.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.community.Exception.CustomizeErrorCode;
import com.example.community.Exception.CustomizeException;
import com.example.community.dto.NotificationDTO;
import com.example.community.dto.PaginationDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.enums.NotificationStatusEnum;
import com.example.community.enums.NotificationTypeEnum;
import com.example.community.model.Notification;
import com.example.community.mapper.NotificationMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import com.example.community.service.NotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zz
 * @since 2020-03-16
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private NotificationServiceImpl notificationService;

    /**
     * 根据id选择出对我的回复,分页
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO selectList(Long userId, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        Integer totalPage;

        QueryWrapper<Notification> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("receiver",userId);
        Integer totalCount=(int) notificationService.count(queryWrapper);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);
        List<Notification> notifications=notificationMapper.selectListByUserId(userId,offset,size);
        List<NotificationDTO> notificationDTOS=new ArrayList<>();

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setList(notificationDTOS);
        return paginationDTO;
    }


    /**
     * 根据userId返回未读数
     * @param userId
     * @return
     */
    public int unreadCount(Long userId) {
        QueryWrapper<Notification> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("receiver",userId).eq("status",NotificationStatusEnum.UNREAD.getStatus());
        return notificationService.count(queryWrapper);
    }

    /**
     * 根据notification的id 对该user 进行已读的处理
     * @param id
     * @param user
     * @return
     */
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectById(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        //设置成已读
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateById(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
