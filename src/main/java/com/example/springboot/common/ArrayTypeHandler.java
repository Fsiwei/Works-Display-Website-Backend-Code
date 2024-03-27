package com.example.springboot.common;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author : siwei.fan
 * @date : 2024/3/17 20:39
 * @modyified By :
 */

public class ArrayTypeHandler extends BaseTypeHandler<String[]> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
		// 将 String 数组转换为字符串，并设置到 PreparedStatement 中
		ps.setString(i, Arrays.toString(parameter));
	}
	
	@Override
	public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// 从 ResultSet 中获取字符串，并转换为 String 数组
		String columnValue = rs.getString(columnName);
		return columnValue != null ? columnValue.split(",") : null;
	}
	
	@Override
	public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// 从 ResultSet 中获取字符串，并转换为 String 数组
		String columnValue = rs.getString(columnIndex);
		return columnValue != null ? columnValue.split(",") : null;
	}
	
	@Override
	public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// 从 CallableStatement 中获取字符串，并转换为 String 数组
		String columnValue = cs.getString(columnIndex);
		return columnValue != null ? columnValue.split(",") : null;
	}
}
