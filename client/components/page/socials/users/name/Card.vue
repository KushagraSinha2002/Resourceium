<template>
  <div
    class="flex flex-col max-w-sm mx-auto lg:max-w-lg md:max-w-md sm:shadow-xl sm:ring-1 ring-gray-300 sm:flex-row rounded-15px"
  >
    <div
      class="relative w-32 h-32 mx-auto sm:h-36 sm:w-36 md:h-40 md:w-40 lg:h-48 lg:w-48"
    >
      <img
        :src="account.imageURL"
        :alt="`account-${account.id}-image`"
        class="w-full h-full transform scale-110 rounded-full sm:rounded-15px sm:mx-0"
      />
      <div
        class="absolute top-0 right-0 flex items-center justify-center w-10 h-10 mx-auto transition duration-700 transform rounded-full shadow-2xl cursor-pointer ring-cream-white sm:left-0 sm:right-0 sm:top-auto -bottom-8 ring hover:scale-105"
        :class="[getFollowingStatus ? 'bg-red-400' : 'bg-emerald-500']"
        @click="toggleFollowing"
      >
        <ig-icon
          :name="getFollowingStatus ? 'x' : 'plus'"
          class="text-white"
          no-color
          size="base"
        ></ig-icon>
      </div>
    </div>
    <div class="flex items-center justify-center flex-1 mt-3 sm:mt-0">
      <div class="flex flex-col">
        <div>
          <h1
            class="text-2xl font-bold text-center text-gray-800 sm:text-xl md:text-2xl font-poppins"
          >
            {{ account.firstName }} {{ account.lastName }}
          </h1>
          <h2 class="text-center text-warm-gray-600 font-poppins">
            <NuxtLink
              :to="{
                name: 'socials-users-username',
                params: { username: `@${account.username}` },
              }"
              class="hover:underline"
            >
              @{{ account.username }}
            </NuxtLink>
          </h2>
        </div>
        <div
          class="flex items-baseline mx-auto mt-2 space-x-4 text-center sm:space-x-2 md:space-x-4 lg:space-x-6 sm:mt-3 font-poppins"
        >
          <div>
            <h1 class="md:text-lg text-warm-gray-800">Files</h1>
            <h2 class="font-semibold font-styled-code text-true-gray-800">
              {{ account.documentCount }}
            </h2>
          </div>
          <div>
            <h1 class="md:text-lg text-warm-gray-800">Following</h1>
            <h2 class="font-semibold font-styled-code text-true-gray-800">
              {{ following.length }}
            </h2>
          </div>
          <div>
            <h1 class="md:text-lg text-warm-gray-800">Followers</h1>
            <h2 class="font-semibold font-styled-code text-true-gray-800">
              {{ followers.length }}
            </h2>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    account: { type: Object, required: true },
    followers: { type: Array, required: true },
    following: { type: Array, required: true },
  },
  computed: {
    getFollowingStatus() {
      for (const f of Array.from(this.followers)) {
        if (f.follower.id === this.$auth.user.id) return true
      }
      return false
    },
  },
  methods: {
    async toggleFollowing() {
      const id = this.account.id
      if (id === this.$auth.user.id) {
        alert('You can not follow yourself.')
        return
      }
      await this.$axios.$post(`/follow/toggle/${id}`)
      this.$emit('refreshStatus')
    },
  },
}
</script>
