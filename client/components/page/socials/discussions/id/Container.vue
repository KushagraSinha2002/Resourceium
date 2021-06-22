<template>
  <div class="flex flex-col w-full h-full overflow-x-hidden">
    <div
      class="flex-none justify-between w-full bg-[#F5F5F7] p-2 flex items-center"
    >
      <div
        v-if="discussion"
        class="flex-1 text-3xl text-left underline md:text-4xl text-warm-gray-800"
      >
        {{ discussion.name }}
      </div>
      <UsersIcon
        size="32"
        class="flex-none text-black cursor-pointer lg:hidden"
        @click="sidebarOpen = !sidebarOpen"
      ></UsersIcon>
    </div>
    <div v-if="discussion" class="relative flex flex-1 overflow-hidden">
      <div
        class="absolute flex flex-col flex-1 p-2 transition-all md:px-5 lg:px-10 xl:px-20 2xl:px-32 lg:static"
        :class="[
          sidebarOpen ? 'right-40 -left-40 md:right-52 md:-left-52' : 'inset-0',
        ]"
      >
        <page-socials-discussions-id-chats
          class="flex-1 mt-2 mb-8 overflow-auto"
        ></page-socials-discussions-id-chats>
      </div>
      <div
        class="absolute flex-none w-40 h-full overflow-auto transition-all bg-true-gray-200 md:w-52 lg:static 2xl:w-64"
        :class="[sidebarOpen ? 'right-0' : '-right-40 md:-right-52']"
      >
        <page-socials-discussions-id-side-bar
          :discussion="discussion"
        ></page-socials-discussions-id-side-bar>
      </div>
    </div>
  </div>
</template>

<script>
import { UsersIcon } from 'vue-feather-icons'

export default {
  components: { UsersIcon },
  data() {
    return {
      discussion: null,
      sidebarOpen: false,
    }
  },
  async fetch() {
    const id = this.$route.params.id
    this.discussion = await this.$axios.$get(`/discussions/${id}`)
  },
}
</script>
