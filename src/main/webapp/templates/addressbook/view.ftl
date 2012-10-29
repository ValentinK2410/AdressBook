[#ftl]

[#assign errors = flash['errors']!/]

[#assign content]
<div class="bo form-actions">
<address>
  <p><strong>
  ${msg['user.firstname']!}:
  </strong></p>
  <p>${contact.firstName}</p>
</address>
  <hr>
<address>
  <p><strong> ${msg['user.lastname']!}:
  </strong></p>
${contact.lastName}
</address>
  <hr>
<address>
  <p><strong>
  ${msg['user.telephone']!}:
  </strong></p>
${contact.telephone}
</address>
  <hr>
<address>
  <p></p><strong>${msg['user.address']!}:
  </strong><p>
${contact.address}
</address>
  <hr>
<address>
  <p></p><strong>${msg['user.comment']!}:
  </strong><p>
${contact.marcComment}
</address>
  <hr>
<address>
  <p></p><strong>
  ${msg['user.email']!}:
  </strong><p>
${contact.mail}
</address>
  <hr>
  <form class="form-horizontal"
        action="/book/${contact.id}/download"
        method="post">
    <table >
      [#if listFiles??]
        [#list listFiles as e]
          <tr>
            <td>
            ${e.name}
              <input type="submit"
                     value ="${msg['user.button.download']!}"/>
                <input type="hidden"
                       name="uuid"
                       value="${e.uuid}"/>
                <input type="hidden"
                       name="name"
                       value="${e.name}" />
            </td>
          </tr>
        [/#list]
      [/#if]
    </table>
  </form>
</div>

<a  class="btn" href="/book">
  <i class="icon-arrow-left">

  </i>${msg['user.button.back']!}</a>
[/#assign]

[#include "../layout.ftl"/]
