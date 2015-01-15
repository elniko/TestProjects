<%--
  Created by IntelliJ IDEA.
  User: stagiaire
  Date: 12/01/2015
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>



<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div>
    <h2>Create a new User</h2>
    <sf:form method="POST" modelAttribute="user">
        <fieldset>
            <table cellspacing="0">
                <tr>
                    <th><label for="user_name">Username:</label></th>
                    <td><sf:input path="name" size="15" id="user_name"/></td>
                </tr>
                <tr>
                    <th><label for="password">Password:</label></th>
                    <td><sf:password path="password" size="15" maxlength="15" id="password"/>
                    </td>
                </tr>
                <tr>
                    <th><label for="email">Email:</label></th>
                    <td><sf:password path="email" size="15" maxlength="15" id="email"/>
                    </td>
                </tr>
                <!--tr>
                    <th><label for="password_check">Repeat password:</label></th>
                    <td><!--sf:password path="password_check" size="15" maxlength="15" id="password_check"/-->
                    <!--/td>
                </tr-->
                <tr>
                    <th><label for="role">Role:</label></th>
                    <td><sf:select multiple="true" path="role" size="4" maxlength="15" id="role" items="${roles}" >


                        </sf:select >
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td>
                        <sf:checkbox path="enabled" value="checked" id="user_enabled" />
                        <label for="user_enabled">User enabled</label>
                    </td>
                </tr>
                <tr>
                    <td>
                          <input type="submit"/>
                    </td>
                    <td></td>
                </tr>
                <!--tr>
                    <th><label for="user_email">Email Address:</label></th>
                    <td><!--sf:input path="email" size="30"
                                  id="user_email"/>
                        <small>In case you forget something</small>
                    </td>
                </tr-->

            </table>
        </fieldset>
    </sf:form>
</div>

</body>
</html>
