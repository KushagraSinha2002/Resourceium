from rest_framework import status
from rest_framework.parsers import MultiPartParser
from rest_framework.response import Response
from rest_framework.views import APIView


class FileUploadView(APIView):
    parser_classes = (MultiPartParser,)

    def post(self, request, format=None):
        file_obj = request.FILES["file"]
        # print(dir(file_obj))
        return Response(status=status.HTTP_204_NO_CONTENT)
