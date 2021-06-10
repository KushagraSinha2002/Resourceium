from django.urls import path

from . import views

urlpatterns = [
    path(
        "upload/<int:user_id>/<int:folder_id>",
        views.upload_view,
        name="upload_file",
    ),
    path(
        "delete/<int:storageID>",
        views.delete_view,
        name="delete_file",
    ),
    path(
        "download/<int:storageID>",
        views.download_view,
        name="download_file",
    ),
]
