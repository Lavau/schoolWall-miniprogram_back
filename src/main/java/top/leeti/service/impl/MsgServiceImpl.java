package top.leeti.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.leeti.dao.MsgMapper;
import top.leeti.entity.Msg;
import top.leeti.entity.User;
import top.leeti.service.MsgService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class MsgServiceImpl implements MsgService {

    @Resource
    private MsgMapper msgMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Msg> listUnreadMsg() {
        List<Msg> msgs = msgMapper.listUnreadMsgByReceiverId(User.obtainCurrentUser().getStuId());
        msgs.forEach(item -> msgMapper.updateReadOfMsgById(item.getId()));
        return msgs;
    }
}
