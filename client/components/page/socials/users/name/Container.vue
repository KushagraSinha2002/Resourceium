<template>
  <div class="flex flex-col w-full h-full px-4 py-5">
    <div>
      <page-socials-users-name-card
        v-if="account"
        :account="account"
        :followers="followers"
        :following="followings"
        class="flex-none mx-auto"
        @refreshStatus="$fetch()"
      ></page-socials-users-name-card>
    </div>
    <div class="flex items-center flex-1 pt-10 overflow-hidden text-center">
      <div class="w-1/2 h-full overflow-y-auto">
        <div
          class="pb-2 text-xl text-center text-gray-900 underline sm:text-2xl md:text-3xl font-poppins"
        >
          Followers
        </div>
        <div v-if="followers.length > 0" class="space-y-2">
          <NuxtLink
            v-for="follower in followers"
            :key="follower.id"
            :to="{
              name: 'socials-users-username',
              params: { username: `@${follower.follower.username}` },
            }"
            class="block text-lg text-warm-gray-700 hover:underline"
          >
            @{{ follower.follower.username }}
          </NuxtLink>
        </div>
        <div v-else class="text-lg text-green-500">EMPTY</div>
      </div>
      <div class="w-1/2 h-full overflow-y-auto">
        <div
          class="pb-2 text-xl text-center text-gray-900 underline sm:text-2xl md:text-3xl font-poppins"
        >
          Following
        </div>
        <div v-if="followings.length > 0" class="space-y-2">
          <NuxtLink
            v-for="following in followings"
            :key="following.id"
            :to="{
              name: 'socials-users-username',
              params: { username: `@${following.following.username}` },
            }"
            class="block text-lg text-warm-gray-700 hover:underline"
          >
            @{{ following.following.username }}
          </NuxtLink>
        </div>
        <div v-else class="text-lg text-green-500">EMPTY</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      account: null,
      followers: [],
      followings: [],
    }
  },
  async fetch() {
    const username = this.$route.params.username
    try {
      const details = await this.$axios.$get(`/accounts/${username}`)
      this.account = details
      const id = details.id
      const followers = await this.$axios.$get(`/follow/followers/${id}`)
      this.followers = followers
      const following = await this.$axios.$get(`/follow/following/${id}`)
      this.followings = following
    } catch {
      if (process.server) {
        this.$nuxt.context.res.statusCode = 404
      }
      throw new Error('This user does not exist')
    }
  },
}
</script>
