<#import "blocks/template.ftlh" as t>
<@t.template user>

    <h1 style="text-align: center">Adverts from all over DUBKI</h1>
    <hr>
    <form method="get" action="/">
        <div class="form-row">
            <div class="form-group col-md-2">
                <label for="searchCategory">Category</label>
                <select id="searchCategory" class="form-control" name = "searchCategory">
                    <option value="">All</option>
                    <option value="PRODUCT_FOOD" <#if searchCategory?? && searchCategory=="PRODUCT_FOOD">selected</#if>>Products and food</option>
                    <option value="ELECTRONICS" <#if searchCategory?? && searchCategory=="ELECTRONICS">selected</#if>>Electronics</option>
                    <option value="SERVICE" <#if searchCategory?? && searchCategory=="SERVICE">selected</#if>>Services</option>
                    <option value="WEAR" <#if searchCategory?? && searchCategory=="WEAR">selected</#if>>Wear</option>
                    <option value="LOST_AND_FOUND" <#if searchCategory?? && searchCategory=="LOST_AND_FOUND">selected</#if>>Lost&Found</option>
                    <option value="OTHER" <#if searchCategory?? && searchCategory=="OTHER">selected</#if>>Other</option>
                </select>
            </div>
            <div class="form-group col-md-2">
                <label for="searchCorpus">Corpus</label>
                <select id="searchCorpus" class="form-control" name = "searchCorpus">
                    <option value="">All</option>
                    <option value=1 <#if searchCorpus?? && searchCorpus == 1>selected</#if>>First</option>
                    <option value=2 <#if searchCorpus?? && searchCorpus == 2>selected</#if>>Second</option>
                    <option value=3 <#if searchCorpus?? && searchCorpus == 3>selected</#if>>Third</option>
                </select>
            </div>
            <div class="form-group col-md-8">
                <label for="searchWord">Find advert by key words</label>
                <input type="text" name="searchWord" <#if searchWord??>value="${searchWord}"</#if> class="form-control"
                       id="searchWord">
            </div>
            <button style="width: 100%" type="submit" class="btn btn-dark">Search</button>
        </div>
    </form>

    <#if user.email??>
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
           data-toggle="collapse" href="#collapseExample" role="button"
           aria-expanded="true" aria-controls="collapseExample">
            Create new advert
        </a>
        <div class="collapse mb-3" id="collapseExample" style="">
            <form action="/advert/create" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="advertName">Advert title</label>
                    <input type="text" required class="form-control"
                           id="advertName" name="title">
                </div>
                <div class="form-group">
                    <label for="price">Price</label>
                    <div class="input-group mb-3">
                        <input type="number" min="0" required class="form-control" id="price" name="price">
                        <div class="input-group-append">
                            <span class="input-group-text">???</span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="corpus">Corpus</label>
                    <select id="corpus" required name="corpus" class="form-control">
                        <option value="">Corpus isn't selected</option>

                        <option value=1>First</option>
                        <option value=2>Second</option>
                        <option value=3>Third</option>

                    </select>
                </div>
                <div class="form-group">
                    <label for="category">Category</label>
                    <select id="category" required name="category" class="form-control">
                        <option value="">Category isn't selected</option>
                        <option value="PRODUCT_FOOD">Products and food</option>
                        <option value="ELECTRONICS">Electronics</option>
                        <option value="SERVICE">Services</option>
                        <option value="WEAR">Wear</option>
                        <option value="LOST_AND_FOUND">Lost&Found</option>
                        <option value="OTHER">Other</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea required class="form-control" id="description" name="description"></textarea>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file1" id="file1">
                        <label class="custom-file-label" for="file1">First picture</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file2" id="file2">
                        <label class="custom-file-label" for="file2">Second picture</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file3" id="file3">
                        <label class="custom-file-label" for="file3">Third picture</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <button type="submit" style="width: 100%" class="btn btn-dark">Create advert</button>
            </form>
        </div>
    </#if>


    <hr>
    <div class="card-columns">
        <#list adverts as advert>
            <a href="/advert/${advert.id}" class="advert-link">
                <div class="card text-white bg-dark my-3 advert">

                    <#if advert.previewImageId??>
                        <img src="/images/${advert.previewImageId}"
                             class="card-img-top">
                    <#else>
                        <img src="/static/images/advert-image.png"
                             class="card-img-top">
                    </#if>

                    <div class="card-body">
                        <h5 class="card-title">${advert.title}</h5>
                        <p class="card-text">${advert.price} ???</p>
                    </div>
                    <div class="card-footer">
                        Corpus ${advert.corpus}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">No adverts</h1>
        </#list>
    </div>



<#-- ???????????? ??????????   -->

<#--    <#if user.email??>-->
<#--        <h3>Welcome, <i>${user.name}</i>!</h3>-->
<#--        <form action="/logout" method="post">-->
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
<#--            <input type="submit" value="Log out"/>-->
<#--        </form>-->
<#--        <#if user.isAdmin()>-->
<#--            <a href="/admin">Administrator panel</a>-->
<#--        </#if>-->
<#--    <#else>-->
<#--        <a href="/login">Sign in</a>-->
<#--    </#if>-->
<#--    <hr>-->

<#--    <form action="/" method="get">-->
<#--        Find advert by its title: <input type="text" name="title"><br>-->
<#--        <input type="submit" value="search">-->
<#--    </form>-->


<#--    <#list adverts as elem>-->

<#--        <div>-->
<#--            <p><b>${elem.title}</b> ${elem.price} ??????. : <a href="/advert/${elem.id}">More...</a></p>-->
<#--        </div>-->
<#--    <#else>-->
<#--        <h3>No goods...</h3>-->

<#--    </#list>-->



<#--    <#if user.email??>-->

<#--        <hr>-->

<#--        <h3>Create new advert</h3>-->
<#--        <form action="/advert/create" method="post" enctype="multipart/form-data">-->
<#--            Advert title: <input type="text" name="title"/><br><br>-->
<#--            Advert description: <input type="text" name="description"><br><br>-->
<#--            Good's price: <input type="number" name="price"/><br><br>-->
<#--            Corpus: <input type="number" name="corpus"/><br><br>-->
<#--            &lt;#&ndash;        Author name: <input type="text" name="authorName"/><br><br>&ndash;&gt;-->
<#--            First image: <input type="file" name="file1"><br><br>-->
<#--            Second image: <input type="file" name="file2"><br><br>-->
<#--            Third image: <input type="file" name="file3"><br><br>-->
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
<#--            <input type="submit" value="Create advert"/>-->
<#--        </form>-->
<#--    </#if>-->

</@t.template>