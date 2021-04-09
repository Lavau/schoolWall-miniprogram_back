package top.leeti.service;

import top.leeti.entity.Msg;

import java.util.List;

public interface MsgService {
    List<Msg> listUnreadMsg();
}
