<template>
  <div
    class="grid grid-cols-2 gap-6 px-2 py-5 bg-cream-white md:py-10 sm:px-10 md:grid-cols-2 xl:grid-cols-5 2xl:grid-cols-6 auto-rows-min"
  >
    <div
      v-for="(folder, name, index) in folders"
      :key="index"
      class="relative flex flex-col items-center p-2 md:p-4 bg-steel-100 rounded-15px"
    >
      <FontAwesomeIcon
        :icon="['fas', 'ellipsis-v']"
        class="absolute h-4 transition-transform transform cursor-pointer bg-denim-50 md:h-6 right-3 md:top-4 top-3 md:right-4 hover:scale-110"
      ></FontAwesomeIcon>
      <ig-icon
        no-size
        no-color
        name="folder"
        class="h-16 mx-auto sm:h-24 text-amber-500"
      ></ig-icon>
      <div class="text-center line-clamp-2 font-poppins">
        {{ folder.title }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  async asyncData({ params, $axios, $moment }) {
    const resp = await $axios.$get(`/folders/${params.id}`)
    resp.map((element) => {
      element.dateOfUpload = $moment(element.dateOfUpload)
      element.lastEdited = $moment(element.lastEdited)
      return element
    })
    return { folders: resp }
  },
}
</script>
