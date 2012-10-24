[#ftl]

[#assign errors = flash['errors']!/]

[#assign content]
<div class="panell">
<table class="table table-striped">
  [#if contacts?size>0]
    [#list contacts as e]
        <tr>
          <td>${e.title}</td>
          <td>${e.mail}</td>
          <td><a class="btn" href="/book/${e.id}""><i class="icon-list-alt"></i>${msg['user.button.review']}</a></td>
          <td><a class="btn" href="/book/${e.id}/edit"><i class="icon-pencil"></i>${msg['user.button.edit']}</a></td>
          <td><a class="btn" href=""><i class="icon-remove"></i>${msg['user.button.delete']}</a></td>
        </tr>
      [/#list]
  [#else]
    <p>${msg['contacts.empty']}</p>
  [/#if]
</table>
 </div>
[/#assign]

[#include "../layout.ftl"/]