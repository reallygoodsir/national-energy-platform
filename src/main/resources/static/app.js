function showLogin() {
    document.getElementById("loginForm").classList.remove("hidden");
    document.getElementById("signupForm").classList.add("hidden");

    document.getElementById("loginTab").classList.add("active");
    document.getElementById("signupTab").classList.remove("active");
}

function showSignup() {
    document.getElementById("signupForm").classList.remove("hidden");
    document.getElementById("loginForm").classList.add("hidden");

    document.getElementById("signupTab").classList.add("active");
    document.getElementById("loginTab").classList.remove("active");
}

async function login() {

    const email = document.getElementById("loginEmail").value;
    const password = document.getElementById("loginPassword").value;

    const res = await fetch("/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
    });

    const data = await res.json();

    const errorBox = document.getElementById("loginError");
    errorBox.innerHTML = "";

    if (!res.ok) {
        errorBox.innerText = data.message || "Invalid login";
        return;
    }

    showSuccessScreen();
}

async function signup() {

     const fullName = document.getElementById("signupName").value;
     const email = document.getElementById("signupEmail").value;
     const phoneNumber = document.getElementById("signupPhone").value;
     const password = document.getElementById("signupPassword").value;

     const res = await fetch("/api/auth/signup", {
         method: "POST",
         headers: {
             "Content-Type": "application/json"
         },
         body: JSON.stringify({
             fullName,
             email,
             phoneNumber,
             password
         })
     });

     const data = await res.json();

     const errorBox = document.getElementById("signupError");
     errorBox.innerHTML = "";

     if (!res.ok) {

         if (data.errors) {

             errorBox.innerHTML = Object.values(data.errors)
                 .map(err => `<div>${err}</div>`)
                 .join("");

         } else {
             errorBox.innerText = data.message || "Signup failed";
         }

         return;
     }

     const loginRes = await fetch("/api/auth/login", {
         method: "POST",
         headers: {
             "Content-Type": "application/json"
         },
         body: JSON.stringify({ email, password })
     });

     if (loginRes.ok) {
         showSuccessScreen();
     }
 }

function showSuccessScreen() {
    document.querySelector(".container").classList.add("hidden");
    document.getElementById("successScreen").classList.remove("hidden");
}