package com.rongyi.core.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import sun.applet.Main;

/**
 * mongo ObjectId集合转换工具类
 *
 * @author Breggor
 */
public abstract class ObjectIdUtils {
    public static List<ObjectId> convertToObjectId(List<String> ids) {
        List<ObjectId> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ids)) {
            for (String id : ids) {
                if (id != null)
                    result.add(new ObjectId(id));
            }
        }
        return result;
    }

    public static List<String> convertToString(List<ObjectId> ids) {
        List<String> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ids)) {
            for (ObjectId id : ids) {
                if (id != null)
                    result.add(id.toString());
            }
        }
        return result;
    }
}
