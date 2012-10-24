[#ftl]

[#assign errors = flash['errors']!/]

[#assign content]

<div class="panell well well-small">
<address>
  <strong>
  ${msg['user.firstname']!}:
  </strong><br>
${contact.firstName}
</address>
  <hr>
<address>
  <strong> ${msg['user.lastname']!}:
  </strong><br>
${contact.lastName}
</address>
  <hr>
<address>
  <strong>
  ${msg['user.telephone']!}:
  </strong><br>
${contact.telephone}
</address>
  <hr>
<address>
  <strong>${msg['user.address']!}:
  </strong><br>
${contact.address}
</address>
  <hr>
<address>
  <strong>${msg['user.comment']!}:
  </strong><br>
${contact.comment}
</address>
  <hr>
<address>
  <strong>
  ${msg['user.email']!}:
  </strong><br>
${contact.mail}
</address>
 </div>
<a  class="btn" href="/book">${msg['user.button.back']!}</a>
[/#assign]

[#include "../layout.ftl"/]