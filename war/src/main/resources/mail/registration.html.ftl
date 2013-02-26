<#-- @ftlvariable name="activationURL" type="java.lang.String" -->
<#-- @ftlvariable name="user" type="com.project.domain.User" -->

<html xmlns="http://www.w3.org/1999/xhtml">
<body>

Dear ${user.getFirstName()}, <br/>
<br/>
Thank you for registering at our website.<br/>
Please click the following link to activate your account:<br/>

<a href="${activationURL}">${activationURL}</a><br/>

Thank you.<br/>

Your Website Team

</body>
</html>
