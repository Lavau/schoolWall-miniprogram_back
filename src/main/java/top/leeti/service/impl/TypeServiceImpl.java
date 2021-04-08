package top.leeti.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.leeti.dao.TypeMapper;
import top.leeti.entity.Type;
import top.leeti.service.TypeService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeMapper typeMapper;

    @Override
    public List<Type> listTypes() {
        return typeMapper.listTypes();
    }
}
