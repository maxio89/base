<#-- @ftlvariable name="resetURL" type="java.lang.String" -->
<#-- @ftlvariable name="user" type="com.project.domain.User" -->
<html xmlns="http://www.w3.org/1999/xhtml">
<body>

Dear ${user.getFirstName()}, <br/>
<br/>
We received your password reset request.
<br/><br/>
If it was your request, please click the following link to change your password for account ${user.getEmail()}:
<br/><br/>
<a href="${resetURL}">${resetURL}</a><br/>

Thank you.<br/>

Your Website Team

</body>
</html>