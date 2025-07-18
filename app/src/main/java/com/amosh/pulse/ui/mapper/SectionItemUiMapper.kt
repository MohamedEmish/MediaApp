package com.amosh.pulse.ui.mapper

import com.amosh.pulse.core.domain.mapper.Mapper
import com.amosh.pulse.core.domain.model.Section
import com.amosh.pulse.core.domain.utils.stringToEnum
import com.amosh.pulse.model.SectionsUiItem
import com.amosh.pulse.model.enums.ContentType
import javax.inject.Inject

class SectionItemUiMapper @Inject constructor(
    private val contentMapper: ContentItemUiMapper
) : Mapper<Section, SectionsUiItem> {
    override fun from(i: Section, parentType: String?): SectionsUiItem {
        return SectionsUiItem(
            name = i.name,
            type = i.type,
            contentType = i.contentType?.let { stringToEnum<ContentType>(it) },
            order = i.order,
            content = contentMapper.fromList(i.content, i.contentType)
        )
    }

    override fun to(o: SectionsUiItem): Section {
        return Section(

        )
    }
}