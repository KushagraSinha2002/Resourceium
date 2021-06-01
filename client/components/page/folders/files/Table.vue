<template>
  <div class="flex flex-col overflow-y-auto h-96">
    <div class="flex-grow overflow-auto">
      <table class="relative w-full border">
        <thead>
          <tr>
            <th class="sticky top-0 px-6 py-3 text-red-900 bg-red-300">Name</th>
            <th class="sticky top-0 px-6 py-3 text-red-900 bg-red-300">
              Uploaded On
            </th>
            <th class="sticky top-0 px-6 py-3 text-red-900 bg-red-300">
              Download Link
            </th>
          </tr>
        </thead>
        <tbody class="bg-red-100 divide-y">
          <tr v-for="file in files" :key="file.id">
            <td class="px-6 py-4 text-center">{{ file.name }}</td>
            <td class="px-6 py-4 text-center">
              {{ $dayjs(file.dateOfUpload).format('D MMM, h:mm A') }}
            </td>
            <td class="px-6 py-4 text-center">
              {{ $config.storageServer + file.url }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    files: { type: Array, required: true },
  },
  mounted() {
    fetch('http://127.0.0.1:8080/files/download/80').then((response) =>
      console.log(response.headers.get('content-length'))
    )
  },
}
</script>
