<#import "blocks/template.ftlh" as t>
<@t.template user>

    <div>

        <h4>Current advert pictures</h4>
        <#list advert.images as image>
            <img src="/images/${image.id}" style="border:2px solid black; border-radius: 20px" height="120">
            <form action="/images/${image.id}/delete" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="hidden" name="advertId" value="${advert.id}">
                <button type="submit" class="btn btn-dark">Delete</button>
            </form>
        <#else>
            <h5>No pictures have been added</h5>
        </#list>

        <form action="/advert/${advert.id}/edit" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="advertName">Advert title</label>
                <input type="text" required class="form-control"
                       id="advertName" name="title" value="${advert.title}">
            </div>
            <div class="form-group">
                <label for="price">Price</label>
                <div class="input-group mb-3">
                    <input type="number" min="0" required class="form-control" id="price" name="price"
                           value="${advert.getStringPrice()}">
                    <div class="input-group-append">
                        <span class="input-group-text">₽</span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="corpus">corpus</label>
                <select id="corpus" required name="corpus" class="form-control">
                    <option <#if advert.corpus==1>selected</#if> value=1>First</option>
                    <option <#if advert.corpus==2>selected</#if> value=2>Second</option>
                    <option <#if advert.corpus==3>selected</#if> value=3>Third</option>
                </select>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea required class="form-control" id="description"
                          name="description">${advert.description}</textarea>
            </div>



            <h3>Add/change pictures</h3>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file1" id="file1">
                    <label class="custom-file-label" for="file1">First photo</label>
                </div>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file2" id="file2">
                    <label class="custom-file-label" for="file2">Second photo</label>
                </div>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file3" id="file3">
                    <label class="custom-file-label" for="file3">Third photo</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <div>
                <button type="submit" style="width: 100%" class="btn btn-dark">Submit</button>
            </div>
        </form>
        <br><br>
        <a href="/my/adverts/">
            <button type="submit" style="width: 100%" class="btn btn-dark">Cancel</button>
        </a>
    </div>
</@t.template>