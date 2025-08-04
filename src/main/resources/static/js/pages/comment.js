let page = 0;


document.addEventListener('DOMContentLoaded', function() {
	fetchComments();
	enableCommentSave();
	document.getElementById('loadMoreBtn').addEventListener('click', fetchComments);

});

function enableCommentSave() {
	const commentForm = document.querySelector("#commentForm");

	if (!commentForm) return;
	const flashMessage = commentForm.querySelector("#flashMessage");

	function flashAlert(status, message) {
		flashMessage.classList.add(status == "success" ? "alert-success" : "alert-danger");
		flashMessage.classList.add("show");
		flashMessage.innerHTML = message;

		setTimeout(function() {
			flashMessage.classList.remove("show");
		}, 2000);
	}

	commentForm.addEventListener('submit', function(event) {
		event.preventDefault();

		const formData = new FormData(commentForm);

		const csrfTokenMeta = document.querySelector('meta[name="_csrf"]');
		const csrfHeaderMeta = document.querySelector('meta[name="_csrf_header"]');

		const csrfToken = csrfTokenMeta ? csrfTokenMeta.getAttribute('content') : null;
		const csrfHeader = csrfHeaderMeta ? csrfHeaderMeta.getAttribute('content') : null;

		const headers = csrfToken && csrfHeader ? { [csrfHeader]: csrfToken } : {};

		fetch("/recipe/comment", {
			method: 'POST',
			headers: headers,
			body: formData
		})
			.then(response => {
				if (!response.ok) throw new Error('Network error');
				return response.json();
			})
			.then(data => {

				if (data.success) {
					flashAlert("success", data.success);
				} else if (data.error) {
					flashAlert("error", data.error);
				}

				commentForm.reset();
				
				page = 0;
				document.getElementById('commentSection').innerHTML  = "";
				fetchComments();
			})
			.catch(error => {
				console.error('Error:', error);
				flashAlert("error", "Error: " + error.message);
			});


	});
}

function fetchComments() {
	const loadMoreBtn = document.getElementById('loadMoreBtn');
	const commentSection = document.getElementById('commentSection');
	
	const recipeId = commentSection.getAttribute('data-recipe-id');

	const url = `/recipe/comments?recipeId=${recipeId}&page=${page}`;
	fetch(url)
		.then(response => {
			if (!response.ok) throw new Error("Network error");
			return response.json();
		})
		.then(data => {
			document.getElementById('commentSection').insertAdjacentHTML('beforeend', data.html);

			if (data.isLastPage || !data.html.trim()) {
				document.getElementById('loadMoreBtn').style.display = 'none';
			} else {
				page++;
			}
		})
		.catch(error => console.error("Error loading comments:", error));

}

