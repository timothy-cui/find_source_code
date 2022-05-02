package com.thoughtmechanix.organization.events.source;

import com.thoughtmechanix.organization.events.models.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import com.thoughtmechanix.organization.utils.UserContext;


@Component
public class SimpleSourceBean {
    private Source source;
    @Autowired
    private UserContext userContext;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    // spring cloud stream将注入一个Source接口，以供服务使用。
    @Autowired
    public SimpleSourceBean(Source source) {
        this.source = source;
    }

    public void publishOrgChange(String action, String orgId) {
        logger.info("Sending Kafka message {} for Organization Id: {}", action, orgId);
        // 将要发布的消息[pojo]。
        OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                orgId,
                userContext.getCorrelationId());

        // 使用Source类中定义的通道的send()方法准备发送消息。
        source.output().send(MessageBuilder.withPayload(change).build());
    }
}
