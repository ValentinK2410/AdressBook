[#ftl]

[#assign errors = flash['errors']!/]

[#assign content]
  [#if errors??]
  <ul>
    [#list errors as e]
      <li>${e}</li>
    [/#list]
  </ul>
  [/#if]
<h3>${msg['user.book.new.add']!}</h3>
<form class="form-horizontal"
      action="/book"
      method="post">

   <div class="control-group">
    <label class="control-label" for="inputFirstName">${msg['user.firstname']!}</label>
    <div class="controls">
      <input type="text" id="inputFirstName" name="bfirstname" placeholder=${msg['user.textbox.placeholder.firstname']}>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="inputLastname">${msg['user.lastname']!}</label>
    <div class="controls">
      <input type="text" id="inputLastname" name="blastname" placeholder=${msg['user.textbox.placeholder.lastname']}>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="inputteLephone">${msg['user.telephone']!}</label>
    <div class="controls">
      <input type="text" id="inputteLephone" name="btelephone" placeholder=${msg['user.textbox.placeholder.telephone']}>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="inputAddress">${msg['user.address']!}</label>
    <div class="controls">
      <input type="textarea" rows="3" id="inputAddress" name="baddress" placeholder=${msg['user.textbox.placeholder.address']}>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="inputComment">${msg['user.comment']!}</label>
    <div class="controls">
      <input type="text" id="inputComment" name="bcomment" placeholder=${msg['user.textbox.placeholder.comment']}>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="inputEmail">${msg['user.email']!}</label>
    <div class="controls">
      <input type="text" id="inputEmail" name="bemail" placeholder=${msg['user.textbox.placeholder.Email']}>
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <button type="submit" class="btn">${msg['send']}</button>
    </div>
  </div>
</form>


[/#assign]

[#include "../layout.ftl"/]