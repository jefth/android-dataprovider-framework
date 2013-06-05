package net.yoojia.dataprovider.annotation;

import java.lang.annotation.*;

/**
 * author : 桥下一粒砂 (chenyoca@gmail.com)
 * date   : 2013-06-05
 * 非数据表列属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Inherited
public @interface NotColumn {
}
