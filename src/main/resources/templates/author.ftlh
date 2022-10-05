<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Project author | Автор проекта</h1>
    <hr>
    <div style="text-align: center">
        <img src="/static/images/author-photo.jpg"
             style="border:2px solid black; border-radius: 20px" height="600">
        <br>
        <br>
        <h2><i>Dzhaparov Emirkhan</i></h2>
        <br>
        <div class="text-info">
            <p>HSE Faculty of Computer Science | ВШЭ Факультет Компьютерных Наук</p>
            <p>Software engineer | Программный инженер</p>
            <p>emirdzhapar@gmail.com</p>
        </div>
    </div>
    <br>
    <hr>
</@t.template>