package com.gitee.pagesmanager.server.config;

import com.gitee.fastmybatis.core.support.DateFillUpdate;
import org.apache.commons.lang.math.NumberUtils;

import java.util.Date;

/**
 * update时的字段填充<br>
 * 在做insert或update操作时,如果表里面有gmt_update字段,则自动填充时间
 * @author tanghc
 *
 */
public class DatetimeFillUpdate extends DateFillUpdate {

	@Override
	protected Date convertValue(Object columnValue) {
		String datetime = String.valueOf(columnValue);
		if (NumberUtils.isNumber(datetime)) {
			return new Date(Long.valueOf(datetime));
		}
		return super.convertValue(columnValue);
	}
	
}