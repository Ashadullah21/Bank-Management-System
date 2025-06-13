function validateRegister() {
  const name = document.getElementById("name").value.trim();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();
  if (!name || !email || !password) {
    alert("All fields are required!");
    return false;
  }
  return true;
}
function validateLogin() {
  const email = document.getElementById("loginEmail").value.trim();
  const password = document.getElementById("loginPassword").value.trim();
  if (!email || !password) {
    alert("Email and Password required!");
    return false;
  }
  return true;
}
function validateTransaction() {
  const amount = document.getElementById("amount").value;
  if (amount <= 0) {
    alert("Enter a valid amount!");
    return false;
  }
  return true;
}