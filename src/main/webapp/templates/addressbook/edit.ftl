[#ftl]

[#assign errors = flash['errors']!/]

[#assign content]

<form class="form-horizontal"
      action="/book/${contact.id}/update"
      method="post">


  <div class="control-group">
    <label class="control-label"
           for="inputFirstName">${msg['user.firstname']!}</label>
    <div class="controls">
      <input type="text"
             id="inputFirstName"
             name="bfirstname"
             value ="${contact.firstName}"">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label"
           for="inputLastname">${msg['user.lastname']!}</label>
    <div class="controls">
      <input type="text"
             id="inputLastname"
             name="blastname"
             value ="${contact.lastName}"">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label"
           for="inputteLephone">${msg['user.telephone']!}</label>
    <div class="controls">
      <input type="text"
             id="inputteLephone"
             name="btelephone"
             value ="${contact.telephone}">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label"
           for="inputAddress">${msg['user.address']!}</label>
    <div class="controls">
      <input type="text"
             rows="3"
             id="inputAddress"
             name="baddress"
             value ="${contact.address}">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label"
           for="inputComment">${msg['user.comment']!}</label>
    <div class="controls">
      <input type="text"
             id="inputComment"
             name="bcomment"
             value ="${contact.comment}"">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label"
           for="inputEmail">${msg['user.email']!}</label>
    <div class="controls">
      <input type="text"
             id="inputEmail"
             name="bemail"
             value ="${contact.mail}">
    </div>
  </div>

  <input type="hidden"
         name="id"
         value ="${contact.id}">
  <div class="form-actions">
    <button type="submit"
            class="btn btn-primary">${msg['user.button.saveChanges']}</button>
    <a  class="btn"
        href="/book">${msg['user.button.cancel']!}</a>
  </div>
</form>

[/#assign]

[#include "../layout.ftl"/]