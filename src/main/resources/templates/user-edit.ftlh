<#--<!DOCTYPE html>-->
<#--<html>-->
<#--<head>-->
<#--    <title>Share, DUBKI</title>-->
<#--</head>-->
<#--<body>-->
<#--<h1>Share, Dubki!</h1>-->
<#--<h3>User ${user.name} editing</h3>-->
<#--<form action="/admin/user/edit" method="post">-->
<#--    <#list roles as role>-->
<#--        <div>-->
<#--            <label>-->
<#--                <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}-->
<#--            </label>-->
<#--        </div>-->
<#--    </#list>-->
<#--    <input type="hidden" value="${user.id}" name="userId">-->
<#--    <input type="hidden" value="${_csrf.token}" name="_csrf">-->
<#--    <button type="submit">Save</button>-->
<#--</form>-->
<#--</body>-->
<#--</html>-->


<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">User ${user.name} editing</h1><hr>
    <form action="/admin/user/edit" method="post">
        <#list roles as role>
            <div>
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-dark">Save</button>
    </form>

</@t.template>