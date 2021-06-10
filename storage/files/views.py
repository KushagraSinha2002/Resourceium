from io import BytesIO

from django.http import HttpResponse
from django.http.response import JsonResponse
from django.shortcuts import get_object_or_404
from django.views.decorators.csrf import csrf_exempt
from django.views.generic import TemplateView
from rest_framework import status

from . import models


class IndexView(TemplateView):
    template_name = "index.html"


@csrf_exempt
def upload_view(request, user_id, folder_id):
    file_obj = request.FILES["file"]
    obj = models.Upload.objects.create(
        file=file_obj,
        user_id=user_id,
        folder_id=folder_id,
        name=file_obj.name,
    )
    obj.save()
    return JsonResponse(data={"storageID": obj.id}, status=status.HTTP_201_CREATED)


def download_view(_, storageID):
    response = HttpResponse()
    buffer = BytesIO()
    file_obj = get_object_or_404(models.Upload, pk=storageID)
    with open(file_obj.file.path, "rb") as in_file:
        buffer.write(in_file.read())
    response.write(buffer.getbuffer())
    response["Content-Disposition"] = f'attachment; filename="{file_obj.name}"'
    return response


@csrf_exempt
def delete_view(_, storageID):
    upload = get_object_or_404(models.Upload, pk=storageID)
    upload.delete()
    return HttpResponse(status=200)
