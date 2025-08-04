
document.addEventListener('DOMContentLoaded', function(){
	enableSubscription();
});

function enableSubscription(){
	const form = document.querySelector("form[name='mc-embedded-subscribe-form']");
    const emailInput = form.querySelector("input[name='email']");
    const submitButton = form.querySelector("button[name='subscribe']");

    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const email = emailInput.value.trim();
        if (!email) {
            Toastify.warning("Please enter your email.");
            return;
        }

        submitButton.disabled = true;
        submitButton.textContent = "Subscribing...";
        
         const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    	const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');


        fetch("/subscribe", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
				"User-Agent": navigator.userAgent,
                  [csrfHeader]: csrfToken
            },
            body: new URLSearchParams({ email })
        })
        .then(response => response.json())
        .then(data => {
			if(data.status == "success"){
	            Toastify.success(data.message);
			}else if(data.status == "error"){
    	        Toastify.error(data.message);
			}else{
        	    Toastify.info(data.message);
			}
        })
        .catch(error => {
            console.error("Error:", error);
            Toastify.error("Something went wrong. Please try again later.");
        })
        .finally(() => {
            submitButton.disabled = false;
            submitButton.textContent = "Subscribe now";
            form.reset();
        });
    });
}