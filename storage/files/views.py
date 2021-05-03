from io import BytesIO

from django.http import HttpResponse
from django.shortcuts import get_object_or_404
from rest_framework import status
from rest_framework.parsers import MultiPartParser
from rest_framework.response import Response
from rest_framework.views import APIView

from . import models


class FileUploadView(APIView):
    parser_classes = (MultiPartParser,)

    def post(self, request, format=None, *args, **kwargs):
        file_obj = request.FILES["file"]
        obj = models.Upload.objects.create(
            file=file_obj,
            user_id=kwargs.get("user_id"),
            folder_id=kwargs.get("folder_id"),
            name=file_obj.name,
        )
        obj.save()
        return Response(data={"storageID": obj.id}, status=status.HTTP_200_OK)


def download_view(request, storageID):
    response = HttpResponse()
    buffer = BytesIO()
    file_obj = get_object_or_404(models.Upload, pk=storageID)
    with open(file_obj.file.path, "rb") as in_file:
        buffer.write(in_file.read())
    response.write(buffer.getbuffer())
    response["Content-Disposition"] = f'attachment; filename="{file_obj.name}"'
    return response
