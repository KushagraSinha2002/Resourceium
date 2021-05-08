from django.contrib import admin
from django.urls import include, path
from files import views

urlpatterns = [
    path("admin/", admin.site.urls),
    path("files/", include("files.urls")),
    path("", views.IndexView.as_view(), name="index_view"),
]
