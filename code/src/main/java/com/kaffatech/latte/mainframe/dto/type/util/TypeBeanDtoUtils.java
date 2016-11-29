package com.kaffatech.latte.mainframe.dto.type.util;

import com.kaffatech.latte.commons.bean.model.NameBean;
import com.kaffatech.latte.commons.bean.model.type.TypeBean;
import com.kaffatech.latte.commons.bean.model.type.BooleanType;
import com.kaffatech.latte.mainframe.dto.type.TypeBeanDto;
import com.kaffatech.latte.commons.toolkit.base.CollectionUtils;
import com.kaffatech.latte.commons.toolkit.base.EnumUtils;
import org.elasticsearch.common.lang3.StringUtils;

import java.util.*;

/**
 * @author zhen.ling
 */
public class TypeBeanDtoUtils {

    public static TypeBeanDto create(TypeBean type) {
        TypeBeanDto typeDto = new TypeBeanDto();
        typeDto.setCode(type.getCode());
        typeDto.setName(type.getName());
        typeDto.setValue(((Enum) type).name().toLowerCase());
        return typeDto;
    }

    public static TypeBeanDto create(String code, String name, String value) {
        TypeBeanDto type = new TypeBeanDto();
        type.setCode(code);
        type.setName(name);
        type.setValue(value);
        return type;
    }

    public static TypeBeanDto convertToSelected(
            Class<? extends Enum> typeClass, TypeBean type) {
        if (type == null) {
            return null;
        }
        return convertToSelected(typeClass, type.getCode());
    }

    public static TypeBeanDto convertToSelected(
            Class<? extends Enum> typeClass, String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }

        List<TypeBeanDto> selectedList = convertToSelectedList(typeClass, code);

        return CollectionUtils.isEmpty(selectedList) ? null : selectedList
                .get(0);
    }

    public static List<TypeBeanDto> convertToSelectedList(
            Class<? extends Enum> typeClass, String selectedCodeInfo) {
        List<TypeBeanDto> selectedList = new ArrayList<TypeBeanDto>();
        List<TypeBeanDto> allList = convertToAllList(typeClass,
                selectedCodeInfo);
        for (TypeBeanDto each : allList) {
            if (each.getSelected()) {
                selectedList.add(each);
            }
        }

        return selectedList;
    }

    public static List<TypeBeanDto> convertToAllList(
            Class<? extends Enum> typeClass) {
        String selectedCodeInfo = null;
        return convertToAllList(typeClass, selectedCodeInfo);
    }

    public static List<TypeBeanDto> convertToAllList(
            Class<? extends Enum> typeClass, TypeBean type) {
        if (type == null) {
            return convertToAllList(typeClass, "");
        }
        return convertToAllList(typeClass, type.getCode());
    }


    public static List<TypeBeanDto> convertToAllList(
            Class<? extends Enum> typeClass, String selectedCodeInfo) {
        List<TypeBeanDto> typeList = new ArrayList<TypeBeanDto>();
        List<String> selectedCodeList = new ArrayList<String>();
        if (!StringUtils.isEmpty(selectedCodeInfo)) {
            selectedCodeList = Arrays.asList(StringUtils
                    .split(selectedCodeInfo));
        }
        List<? extends Enum> enumList = EnumUtils.getEnumList(typeClass);
        for (Enum each : enumList) {
            TypeBeanDto type = new TypeBeanDto();
            type.setCode(((TypeBean) each).getCode());
            type.setName(((TypeBean) each).getName());
            type.setValue(each.name().toLowerCase());
            if (selectedCodeList.contains(((TypeBean) each).getCode())) {
                type.setSelected(true);
            } else {
                type.setSelected(false);
            }
            typeList.add(type);
        }

        return typeList;
    }

    public static List<TypeBeanDto> convertToAllList(
            Class<? extends Enum> typeClass, String selectedCodeInfo, String separator) {
        List<TypeBeanDto> typeList = new ArrayList<TypeBeanDto>();
        List<String> selectedCodeList = new ArrayList<String>();
        if (!StringUtils.isEmpty(selectedCodeInfo)) {
            selectedCodeList = Arrays.asList(StringUtils
                    .split(selectedCodeInfo, separator));
        }
        List<? extends Enum> enumList = EnumUtils.getEnumList(typeClass);
        for (Enum each : enumList) {
            TypeBeanDto type = new TypeBeanDto();
            type.setCode(((TypeBean) each).getCode());
            type.setName(((TypeBean) each).getName());
            type.setValue(each.name().toLowerCase());
            if (selectedCodeList.contains(((TypeBean) each).getCode())) {
                type.setSelected(true);
            } else {
                type.setSelected(false);
            }
            typeList.add(type);
        }

        return typeList;
    }

    public static List<TypeBeanDto> convertToValidList(
            Class<? extends Enum> typeClass, String validCodeInfo) {
        List<TypeBeanDto> validList = new ArrayList<TypeBeanDto>();
        List<String> validCodeList = new ArrayList<String>();
        if (!StringUtils.isEmpty(validCodeInfo)) {
            validCodeList = Arrays.asList(StringUtils
                    .split(validCodeInfo));
        }

        List<? extends Enum> enumList = EnumUtils.getEnumList(typeClass);
        for (Enum each : enumList) {
            if (validCodeList.contains(((TypeBean) each).getCode())) {
                TypeBeanDto type = new TypeBeanDto();
                type.setCode(((TypeBean) each).getCode());
                type.setName(((TypeBean) each).getName());
                type.setValue(each.name().toLowerCase());
                validList.add(type);
            }
        }
        return validList;
    }

    public static List<TypeBeanDto> convertToValidList(
            Class<? extends Enum> typeClass, String validCodeInfo, String selectedCodeInfo) {
        List<TypeBeanDto> typeList = new ArrayList<TypeBeanDto>();
        List<String> validCodeList = new ArrayList<String>();
        if (!StringUtils.isEmpty(validCodeInfo)) {
            validCodeList = Arrays.asList(StringUtils
                    .split(validCodeInfo));
        }
        List<? extends Enum> enumList = EnumUtils.getEnumList(typeClass);
        for (Enum each : enumList) {
            TypeBeanDto type = new TypeBeanDto();
            type.setCode(((TypeBean) each).getCode());
            type.setName(((TypeBean) each).getName());
            type.setValue(each.name().toLowerCase());
            if (StringUtils.equals(selectedCodeInfo, ((TypeBean) each).getCode())) {
                type.setSelected(true);
            } else {
                type.setSelected(false);
            }
            if (validCodeList.contains(((TypeBean) each).getCode())) {
                typeList.add(type);
            }
        }

        return typeList;
    }

    public static List<TypeBeanDto> convertToAllList(List<? extends NameBean> basedList, List<? extends NameBean> selectedList) {
        List<TypeBeanDto> list = new ArrayList<>();

        if (CollectionUtils.isEmpty(basedList)) {
            return list;
        }

        Map<Long, String> temp = new HashMap<>();
        if (!CollectionUtils.isEmpty(selectedList)) {
            for (NameBean each : selectedList) {
                temp.put(each.getId(), null);
            }
        }

        for (NameBean each : basedList) {
            TypeBeanDto typeBeanDto = new TypeBeanDto();
            typeBeanDto.setCode(String.valueOf(each.getId()));
            typeBeanDto.setName(each.getName());
            if (temp.containsKey(each.getId())) {
                typeBeanDto.setSelected(true);
            } else {
                typeBeanDto.setSelected(false);
            }

            list.add(typeBeanDto);
        }

        return list;
    }

    public static List<TypeBeanDto> convertOneToList(List<? extends NameBean> basedList, NameBean selectedElement) {
        if (null != selectedElement) {
            List<NameBean> selectedList = new ArrayList<>();
            selectedList.add(selectedElement);
            return convertToAllList(basedList, selectedList);
        }
        return convertToAllList(basedList, null);
    }

    public static List<TypeBeanDto> convertToAllList(List<? extends NameBean> basedList) {
        return convertToAllList(basedList, null);
    }

    public static TypeBeanDto convertFromBooleanType(String name, BooleanType booleanType) {
        TypeBeanDto typeBeanDto = new TypeBeanDto();
        typeBeanDto.setName(name);
        typeBeanDto.setCode(BooleanType.YES.getCode());
        if (null != booleanType && booleanType.equals(BooleanType.YES)) {
            typeBeanDto.setSelected(true);
        }
        return typeBeanDto;
    }

    public static TypeBeanDto convertFromBooleanType(String name) {
        return convertFromBooleanType(name, null);
    }

}
