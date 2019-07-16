<!DOCTYPE HTML>

<html>
<head>
</head>
<body style="font-family: SimSun;line-height:1">
<#if articleEntitiesList?? >
<#list articleEntitiesList as aelist>

    <#if aelist?? >
        <span>${aelist.title!"没有爬到标题，请自行粘贴"}</span> <br/>
        <span>${aelist.createDate!"没有爬到创建时间，请自行粘贴"}</span> <br/>
        <span>${aelist.articleContent!"没有爬到内容，请自行粘贴"}</span> <br/>
        <span>${aelist.url!"没有爬到url，请自行粘贴"}</span> <br/>
            <span><img src="D:\05.png"></span>
    </#if>
</#list>
</#if>
</body>
</html>



