<#--<!DOCTYPE html>-->
<#--<html>-->
<#--<head>-->
<#--    <title>Share, DUBKI</title>-->
<#--</head>-->
<#--<body>-->
<#--<h1>Share, Dubki!</h1>-->

<#--<h3>${user.name} user info</h3>-->
<#--<b>Номер телефона: </b> ${user.phoneNumber}<br>-->
<#--<b>Email: </b> ${user.email}<br>-->

<#--<h4>Adverts of ${user.name}</h4>-->

<#--<#list adverts as advert>-->
<#--    <div>-->
<#--        <p><b>${advert.title}</b> ${advert.price}₽ | <a href="/advert/${advert.id}">More...</a> </p>-->

<#--    </div>-->
<#--    <#else>-->
<#--    <h3>No adverts...</h3>-->
<#--</#list>-->


<#--<#if errorMessage??>-->
<#--    <h2 style="color: red">${errorMessage}</h2>-->
<#--</#if>-->

<#--</body>-->
<#--</html>-->

<#import "blocks/template.ftlh" as t>
<@t.template userByPrincipal>
    <h1 style="text-align: center">User information</h1>
    <hr>
    <div style="text-align: center">
        <img src="/static/images/avatar.png"
             style="border:2px solid black; border-radius: 20px" height="120">
        <br>
        <br>
        <h2>${user.name}</h2>
        <br>
        Phone number: <b>${user.phoneNumber}</b>
    </div>
    <br>
    <hr>
    <h1 style="text-align: center">${user.name} user's adverts </h1>
    <br>
    <div class="card-columns">
        <#list adverts as advert>
            <a href="/advert/${advert.id}" class="advert-link">
                <div class="card text-white bg-dark my-3 advert">

                    <#if advert.previewImageId??>
                        <img src="/images/${advert.previewImageId}"
                             class="card-img-top">
                    <#else>
                        <img src="/static/images/advert-image-template.jpeg"
                             class="card-img-top">
                    </#if>
                    <div class="card-body">
                        <h5 class="card-title">${advert.title}</h5>
                        <p class="card-text">${advert.price} ₽</p>
                    </div>
                    <div class="card-footer">
                        ${advert.user.name}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">No advert found</h1>
        </#list>
    </div>
</@t.template>