[#ftl]


${msg['user.delete.message']}

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
    ${msg['user.email']!}:
    </strong><br>
  ${contact.mail}
  </address>
</div>
<form class="form-horizontal"
      action="/book/${contact.id}"
      method="post">
  <input type="hidden"
         name="_method"
         value="delete"/>
  <div class="control-group">
    <div class="controls">
      <button type="submit"
              class="btn">
      ${msg['user.button.delete']}
      </button>
    </div>
  </div>
</form>