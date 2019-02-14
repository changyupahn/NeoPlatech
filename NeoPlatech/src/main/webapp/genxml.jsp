<%@page import="boassoft.util.CamelUtil"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%
	String DB_URL = "jdbc:mysql://192.168.0.47:3306/boasrfid_posid";
	String DB_USER = "boasrfid_posid";
	String DB_PASSWORD = "bo392766";
	String TABLE_NAME = "rfid_asset";
	String OBJECT_NAME = CamelUtil.convert2CamelCase(TABLE_NAME).replaceAll("^rfid", "");

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	String query = "select * from " + TABLE_NAME;

	try {

		//Class.forName("com.mysql.jdbc.Driver"); // mysql 연결시
		Class.forName("com.mysql.jdbc.Driver");

		// Connection 가져옴
		conn = DriverManager
				.getConnection(DB_URL, DB_USER, DB_PASSWORD);

		// Statement를 준비
		stmt = conn.createStatement();

		// 쿼리 실행
		rs = stmt.executeQuery(query);

		ResultSetMetaData rsmd = rs.getMetaData();

		int columnCnt = rsmd.getColumnCount(); //컬럼의 수

		String useYnStr = "1=1";
		for (int i=1; i<=columnCnt; i++) {
			String cnm = rsmd.getColumnName(i);
			if ("USE_YN".equalsIgnoreCase(cnm)) {
				useYnStr = "A.USE_YN = 'Y'";
			}
		}
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE sqlMap PUBLIC "-//iBATIS.com//DTD SQL Map 2.0//EN" "http://www.ibatis.com/dtd/sql-map-2.dtd">

<sqlMap namespace="<%=OBJECT_NAME%>">

    <typeAlias  alias="cmap" type="boassoft.util.CommonMap"/>

    <sql id="<%=OBJECT_NAME%>.get<%=OBJECT_NAME%>List.Where">
    </sql>

    <select id="<%=OBJECT_NAME%>.get<%=OBJECT_NAME%>List" parameterClass="cmap" resultClass="cmap" >
    	<include refid="Common.pagingHeader"/>
        SELECT	A.*
        FROM 	<%=TABLE_NAME%> A
        WHERE 	<%=useYnStr%>
		<include refid="<%=OBJECT_NAME%>.get<%=OBJECT_NAME%>List.Where" />
        ORDER BY <% for (int i=1; i<=1; i++) { %><%=rsmd.getColumnName(i)%><% } %> DESC
        <include refid="Common.pagingFooter"/>
    </select>

    <select id="<%=OBJECT_NAME%>.get<%=OBJECT_NAME%>ListCnt" parameterClass="cmap" resultClass="int">
        SELECT	COUNT(*)
        FROM 	<%=TABLE_NAME%> A
        WHERE 	<%=useYnStr%>
		<include refid="<%=OBJECT_NAME%>.get<%=OBJECT_NAME%>List.Where" />
    </select>

    <select id="<%=OBJECT_NAME%>.get<%=OBJECT_NAME%>View" parameterClass="cmap" resultClass="cmap" >
        SELECT	A.*
        FROM 	<%=TABLE_NAME%> A
        WHERE 	<% for (int i=1; i<=1; i++) { %><%if(i>1 && i<=columnCnt){out.print("\r\n\t\t\t\t");}%><%=(i>1?",":"")%><%=rsmd.getColumnName(i)%> = #<%=CamelUtil.convert2CamelCase(rsmd.getColumnName(i))%>#<% } %>
    </select>

    <insert id="<%=OBJECT_NAME%>.insert<%=OBJECT_NAME%>" parameterClass="cmap" >
        INSERT INTO <%=TABLE_NAME%> (
        		<% for (int i=1; i<=columnCnt; i++) { %><%if(i>1 && i<=columnCnt){out.print("\r\n\t\t\t\t");}%><%=(i>1?",":"")%><%=rsmd.getColumnName(i)%><% } %>
        ) VALUES (
				<% for (int i=1; i<=columnCnt; i++) { %><%if(i>1 && i<=columnCnt){out.print("\r\n\t\t\t\t");}%><%=(i>1?",":"")%>#<%=CamelUtil.convert2CamelCase(rsmd.getColumnName(i))%>#<% } %>
        )
    </insert>

    <update id="<%=OBJECT_NAME%>.update<%=OBJECT_NAME%>" parameterClass="cmap" >
        UPDATE 	<%=TABLE_NAME%>
        SET
				<% for (int i=2; i<=columnCnt; i++) { %><%if(i>2 && i<=columnCnt){out.print("\r\n\t\t\t\t");}%><%=(i>2?",":"")%><%=rsmd.getColumnName(i)%> = #<%=CamelUtil.convert2CamelCase(rsmd.getColumnName(i))%>#<% } %>
        WHERE 	<% for (int i=1; i<=1; i++) { %><%if(i>1 && i<=columnCnt){out.print("\r\n\t\t\t\t");}%><%=(i>1?",":"")%><%=rsmd.getColumnName(i)%> = #<%=CamelUtil.convert2CamelCase(rsmd.getColumnName(i))%>#<% } %>
    </update>

    <update id="<%=OBJECT_NAME%>.delete<%=OBJECT_NAME%>" parameterClass="cmap" >
        UPDATE 	<%=TABLE_NAME%>
        SET
				USE_YN = 'N'
				,LAST_UPDT_PNTTM = #lastUpdtPnttm#
				,LAST_UPDUSR_ID = #lastUpdusrId#
        WHERE 	<% for (int i=1; i<=1; i++) { %><%if(i>1 && i<=columnCnt){out.print("\r\n\t\t\t\t");}%><%=(i>1?",":"")%><%=rsmd.getColumnName(i)%> = #<%=CamelUtil.convert2CamelCase(rsmd.getColumnName(i))%>#<% } %>
    </update>

    <delete id="<%=OBJECT_NAME%>.delete<%=OBJECT_NAME%>2" parameterClass="cmap" >
        DELETE
        FROM 	<%=TABLE_NAME%>
        WHERE 	<% for (int i=1; i<=1; i++) { %><%if(i>1 && i<=columnCnt){out.print("\r\n\t\t\t\t");}%><%=(i>1?",":"")%><%=rsmd.getColumnName(i)%> = #<%=CamelUtil.convert2CamelCase(rsmd.getColumnName(i))%>#<% } %>
    </delete>

</sqlMap>
<%
	} catch (Exception e) {
		out.print("Exception Error...");
		out.print(e.toString());
	} finally {
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (conn != null) conn.close();
	}
%>