[#ftl]

[#assign errors = flash['errors']!/]
[#assign content]
<div class="panell">
  <table class="table table-striped">
    [#if contacts?size>0]
      [#list contacts as e]
        <tr>
          <td>${e.title}</td>
          <td>
           <a class="btn"
               href="/book/${e.id}">
              <i class="icon-list"></i>${msg['user.button.view']}
            </a>
          </td>
          <td>
            <a class="btn"
               href="/book/${e.id}/edit">
              <i class="icon-pencil"></i>${msg['user.button.edit']}
            </a>
          </td>
          <td>
            <a class="btn"
               rel="popup"
               href="/book/${e.id}/~delete">
              <i class="icon-remove"></i>${msg['user.button.delete']}
            </a>
          </td>
          <td>
            <a class="btn"
               href="/book/${e.id}/uploads">
              <i class="icon-upload"></i>${msg['user.button.upload']}
            </a>
          </td>
        </tr>
      [/#list]
    [#else]
      <p>${msg['contacts.empty']}</p>
    [/#if]
  </table>
</div>
[/#assign]

[#include "../layout.ftl"/]