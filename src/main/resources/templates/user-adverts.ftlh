<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">My adverts</h1>
    <hr>
    <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
       data-toggle="collapse" href="#collapseExample" role="button"
       aria-expanded="true" aria-controls="collapseExample">
        Add new advert
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
                    <input type="number" required class="form-control" id="price" name="price">
                    <div class="input-group-append">
                        <span class="input-group-text">₽</span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="corpus">Corpus</label>
                <select id="corpus" required name="corpus" class="form-control">
                    <option value="">Select corpus</option>

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

            <button type="submit" style="width: 100%" class="btn btn-dark">Add</button>
        </form>
    </div>
    <div class="card-columns">
        <#list adverts as advert>
            <a href="/advert/${advert.id}" class="advert-link">
                <div class="card text-white bg-dark my-3 advert">
                    <div class="card-top">
                        <form action="/advert/${advert.id}/delete" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-danger m-3">
                                Delete
                            </button>
                        </form>

                        <form action="/advert/${advert.id}/edit" method="get">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-danger m-3">
                                Edit
                            </button>
                        </form>
                    </div>
                    <#if advert.previewImageId??>
                        <img src="/images/${advert.previewImageId}"
                             class="card-img-top">
                        <#else>
                        <img src="/static/images/advert-image.png"
                             class="card-img-top">
                    </#if>

                    <div class="card-body">
                        <h5 class="card-title">${advert.title}</h5>
                        <p class="card-text">${advert.price} ₽</p>
                    </div>
                    <div class="card-footer">
                        ${advert.corpus}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Nothing found</h1>
        </#list>
    </div>

</@t.template>