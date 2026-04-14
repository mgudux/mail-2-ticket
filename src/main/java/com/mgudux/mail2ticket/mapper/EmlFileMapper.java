package com.mgudux.mail2ticket.mapper;

import com.mgudux.mail2ticket.domain.dto.EmlFileDto;
import com.mgudux.mail2ticket.domain.entities.EmlFile;

public interface EmlFileMapper {

    EmlFileDto.Summary toSummary(EmlFile emlFile);
    EmlFileDto.Detail toDetail(EmlFile emlFile);
}
