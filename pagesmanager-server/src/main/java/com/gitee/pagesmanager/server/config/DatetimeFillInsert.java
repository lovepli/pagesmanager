package com.gitee.pagesmanager.server.config;

import com.gitee.fastmybatis.core.support.DateFillInsert;
import org.apache.commons.lang.math.NumberUtils;

import java.util.Date;

/**
 * insert时的字段填充<br>
 * 在做insert操作时,如果表里面有gmt_create字段,则自动填充时间
 * @author tanghc
 *
 */
public class DatetimeFillInsert extends DateFillInsert {

	@Override
	protected Date convertValue(Object columnValue) {
		String datetime = String.valueOf(columnValue);
		if (NumberUtils.isNumber(datetime)) {
			return new Date(Long.valueOf(datetime));
		}
		return super.convertValue(columnValue);
	}

}