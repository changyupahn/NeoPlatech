<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/default.jsp" %>
<%
CommonMap img = RequestUtil.getCommonMap(request, "cmRequest"); //검색값 유지
%>
<img src='/asset/getImage.do?assetNo=<%=img.getString("assetNo")%>&fileDt=<%=img.getString("fileDt")%>' alt="" />
