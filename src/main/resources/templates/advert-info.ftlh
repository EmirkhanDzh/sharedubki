<#import "blocks/template.ftlh" as t>
<@t.template user>
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <#list images as image>
                <li data-target="#myCarousel" data-slide-to="0" class=""></li>
            </#list>
        </ol>
        <div class="carousel-inner">
            <#list images as image>
                <#if image??>
<#--                    <div class="carousel-item <#if image.previewImage>active</#if>">-->
                    <div class="carousel-item <#if image.previewImage>active</#if>">
                        <svg class="bd-placeholder-img" width="100%" height="100%" xmlns="http://www.w3.org/2000/svg"
                             role="img" aria-label=" : " preserveAspectRatio="xMidYMid slice" focusable="false">
                            <rect width="100%" height="100%" fill="#777"></rect>
                            <text x="50%" y="50%" fill="#777" dy=".3em"></text>
                        </svg>

                        <div class="container">
                            <div class="carousel-caption">
                                <a href="/images/${image.id}">
                                    <img class="advert" style="height: 100px" src="/images/${image.id}">
                                </a>
                            </div>
                        </div>
                    </div>
                </#if>
            </#list>

        </div>
        <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
    <hr>
    <p><h1 style="text-align: center">${advert.title} | ${advert.price} ₽</h1></p>
    <p style="text-align: center"><i>${advert.description}</i></p>
    <p style="text-align: center">Corpus <b>№${advert.getCorpus()}</b></p>
    <p style="text-align: center">Category: <b>${advert.getSimpleCategoryName()}</b></p>
    <p style="text-align: center">Seller phone number: <b>${authorAdvert.phoneNumber}</b></p>

    <hr>
    <p style="text-align: center">
        <a href="/user/${authorAdvert.id}">
            <img src="/static/images/avatar.png" alt="mdo" width="32" height="32"
                 class="rounded-circle"> ${authorAdvert.name}
        </a>
    </p>

<#--<hr>-->

<#--<form action="/advert/delete/${advert.id}" method="post">-->
<#--    <input type="submit" value="Delete good"/>-->
<#--</form>-->

</@t.template>