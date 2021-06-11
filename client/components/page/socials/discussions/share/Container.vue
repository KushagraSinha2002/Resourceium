<template>
  <div
    class="flex flex-col w-full h-full px-5 pt-5 pb-10 mx-auto space-y-5 lg:w-2/3 xl:w-1/2 lg:px-0"
  >
    <form class="flex-none">
      <base-input-box
        v-model="discussionName"
        name="name"
        placeholder-text="Enter discussion name"
        class="w-full"
        focus-on-render
        no-hide-label
        no-auto-complete
      ></base-input-box>
    </form>
    <div class="flex flex-col flex-grow overflow-x-hidden overflow-y-auto">
      <div class="flex-none my-3 font-poppins">
        <span class="text-xl sm:text-2xl text-true-gray-600">Share to:</span>
        <span class="text-3xl text-warm-gray-800">{{ selected.length }} </span>
      </div>
      <div class="flex-1 space-y-2">
        <div
          v-for="discussion in filteredDiscussions"
          :key="discussion.id"
          class="p-2 transition-colors duration-300 rounded-md cursor-pointer"
          :class="[
            selected.includes(discussion.id)
              ? 'bg-emerald-200'
              : 'bg-true-gray-100',
          ]"
          @click="toggleSelect(discussion.id)"
        >
          {{ discussion.name }}
        </div>
      </div>
    </div>
    <div class="flex justify-end flex-none">
      <div
        class="transition duration-300 ease-in-out transform bg-blue-500 rounded-full"
        :class="{
          'opacity-0 scale-0 pointer-events-none': selected.length === 0,
        }"
        @click="handleSubmit"
      >
        <img
          :src="require('~/assets/images/send-discussion.png')"
          alt="send icon"
          class="cursor-pointer h-14 md:h-20"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      discussions: [],
      filteredDiscussions: [],
      discussionName: '',
      selected: [],
    }
  },
  watch: {
    discussionName(val) {
      const find = val.toLowerCase()
      this.filteredDiscussions = this.discussions.filter((d) =>
        d.name.toLowerCase().includes(find)
      )
    },
  },
  async mounted() {
    const id = this.$auth.user.id
    this.discussions = await this.$axios.$get(`/discussions/account/${id}`)
    this.filteredDiscussions = this.discussions
  },
  methods: {
    toggleSelect(id) {
      const index = this.selected.indexOf(id)
      if (index === -1) {
        this.selected.push(id)
      } else {
        this.selected.splice(index, 1)
      }
    },
    async handleSubmit() {
      const folderID = this.$route.query.folderId
      for (const id of this.selected) {
        const url = `/post/create/${folderID}/${id}`
        await this.$axios.$post(url)
      }
      this.$router.push({ name: 'socials-discussions' })
    },
  },
}
</script>
