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
 </div>
<a  class="btn" href="/book">${msg['user.button.back']!}</a>
[/#assign]

[#include "../layout.ftl"/]