<template>
  <NuxtLink
    :id="`folder-${folder.id}`"
    :to="{
      name: 'folders-folderId',
      params: { folderId: folder.id },
    }"
    class="relative flex flex-col items-center shadow-lg cv-folder-container"
  >
    <FontAwesomeIcon
      :icon="['fas', 'ellipsis-v']"
      class="absolute h-4 text-black transition duration-500 transform bg-transparent cursor-pointer hover:text-gray-800 md:h-6 right-3 md:top-4 top-3 md:right-4 hover:scale-110 cv-folder-control"
    ></FontAwesomeIcon>
    <div class="flex flex-none w-full p-2 bg-gray-200 md:p-4 rounded-t-15px">
      <ig-icon
        no-size
        no-color
        name="folder"
        class="h-16 mx-auto sm:h-24 text-amber-500"
      ></ig-icon>
    </div>
    <div
      class="flex-grow w-full p-2 text-center rounded-b-md bg-denim-300 line-clamp-2 font-poppins cv-title"
    >
      {{ folder.title }}
    </div>
  </NuxtLink>
</template>

<script>
export default {
  props: {
    folder: {
      required: true,
      type: Object,
    },
  },
}
</script>
