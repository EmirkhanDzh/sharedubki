<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h4>Administrator panel</h4>
    <hr>
    <table border="1">
        <tr>
            <th>Email</th>
            <th>Phone number</th>
            <th>Is active</th>
            <th>Role</th>
            <th>Ban/Unban</th>
            <th>Edit user</th>
            <th>More info</th>
        </tr>
        <#list users as user>
            <tr>
                <th>${user.email}</th>
                <th>${user.phoneNumber}</th>
                <th><#if user.active>true<#else>false</#if></th>
                <th><#list user.roles as role>${role}</#list></th>
                <th>
                    <form action="/admin/user/${user.id}/ban" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <input type="submit" value=<#if user.active>"Ban"<#else>"Unban"</#if>/>
                    </form>
                </th>
                <th><a href="/admin/user/${user.id}/edit">Edit</a></th>
                <th><a href="/user/${user.id}">profile</a></th>
            </tr>
        <#else>
            <h3>No users</h3>
        </#list>
    </table>
</@t.template>