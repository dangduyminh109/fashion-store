package com.fashion_store.mapper;

import com.fashion_store.dto.topic.request.TopicRequest;
import com.fashion_store.dto.topic.response.TopicResponse;
import com.fashion_store.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TopicMapper {
    @Mapping(target = "slug", ignore = true)
    Topic toTopic(TopicRequest topicRequest);

    TopicResponse toTopicResponse(Topic topic);

    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "slug", ignore = true)
    void updateTopic(@MappingTarget Topic topic, TopicRequest topicRequest);
}
