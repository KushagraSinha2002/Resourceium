<template>
  <div
    class="flex flex-col w-full h-full p-2 md:px-5 lg:px-10 xl:px-20 2xl:px-32"
  >
    <div
      v-if="discussion"
      class="flex-none text-3xl underline md:text-4xl text-warm-gray-800"
    >
      {{ discussion.name }}
    </div>
    <page-socials-discussions-id-chats
      class="flex-1 mt-2 mb-8 overflow-auto"
    ></page-socials-discussions-id-chats>
  </div>
</template>

<script>
export default {
  data() {
    return {
      discussion: null,
    }
  },
  async fetch() {
    const id = this.$route.params.id
    this.discussion = await this.$axios.$get(`/discussions/${id}`)
  },
}
</script>
