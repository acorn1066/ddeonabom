import { useState, useEffect } from 'react'


const Icon = {
  pin: (p) => (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.7" strokeLinecap="round" strokeLinejoin="round" {...p}>
      <path d="M12 21s7-5.5 7-11a7 7 0 1 0-14 0c0 5.5 7 11 7 11Z" />
      <circle cx="12" cy="10" r="2.5" />
    </svg>
  ),
  download: (p) => (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" {...p}>
      <path d="M12 3v12" /><path d="m7 11 5 5 5-5" /><path d="M5 21h14" />
    </svg>
  ),
  refresh: (p) => (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" {...p}>
      <path d="M3 12a9 9 0 0 1 15-6.7L21 8" /><path d="M21 3v5h-5" />
      <path d="M21 12a9 9 0 0 1-15 6.7L3 16" /><path d="M3 21v-5h5" />
    </svg>
  ),
  clock: (p) => (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.7" strokeLinecap="round" strokeLinejoin="round" {...p}>
      <circle cx="12" cy="12" r="9" /><path d="M12 7v5l3 2" />
    </svg>
  ),
  bolt: (p) => (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.7" strokeLinecap="round" strokeLinejoin="round" {...p}>
      <path d="M13 2 4 14h7l-1 8 9-12h-7l1-8Z" />
    </svg>
  ),
}

function fmt(d) {
  if (!d) return null
  const p = (n) => String(n).padStart(2, '0')
  return `${d.getFullYear()}.${p(d.getMonth() + 1)}.${p(d.getDate())} ${p(d.getHours())}:${p(d.getMinutes())}`
}

function StatCard({ label, value, caption, accent }) {
  const big = !value || value.length <= 7
  return (
    <div className="rounded-xl border border-line bg-white p-5 shadow-card">
      <div className="text-[13px] font-semibold text-muted">{label}</div>
      <div className={`my-2 font-extrabold tracking-[-0.03em] tabular-nums ${big ? 'text-[32px] leading-none' : 'text-2xl'} ${accent ? 'text-cobalt' : 'text-ink'}`}>
        {value ?? <span className="font-bold text-faint">—</span>}
      </div>
      <div className="text-[13px] text-faint">{caption}</div>
    </div>
  )
}

/* ── 관광지 관리 ───────────────────────────────────────── */
export default function Landmark() {
  const DAILY_LIMIT = 1000

  const [syncLog, setSyncLog] = useState(null)
  const [busy, setBusy] = useState(null)

  const [toast, setToast] = useState(null)

  // 상태 조회
  const fetchStatus = async () => {
    const res = await fetch('/react/admin/landmark/status')
    const data = await res.json()
    setSyncLog(data)
  }

  const showToast = (msg, type = 'ok') => {
    setToast({ msg, type })
    setTimeout(() => setToast(null), 3000)
  }

  useEffect(() => {
    fetchStatus()
  }, [])

  const totalPages = syncLog?.totalPages ?? 0
  const page = syncLog?.lastPage ?? 0
  const calls = syncLog?.dailyCalls ?? 0
  const pct = totalPages ? Math.round((page / totalPages) * 100) : 0
  const callPct = Math.min(100, Math.round((calls / DAILY_LIMIT) * 100))
  const done = totalPages > 0 && page >= totalPages
  console.log('totalPages:', totalPages, 'page:', page, 'done:', done)
  // 이어서 수집
  const collect = async () => {
    if (busy || done) return
    setBusy('collect')
    try {
      const res = await fetch('/react/admin/landmark/collect', { method: 'POST' })
      const data = await res.json()
      setSyncLog(data)
      showToast('수집 완료!')  // ← 여기
    } catch (e) {
      showToast('수집 중 오류가 발생했어요.', 'err')  // ← 여기
    } finally {
      setBusy(null)
    }
  }

  // 전체 업데이트
  const update = async () => {
    if (busy) return
    setBusy('update')
    try {
      const res = await fetch('/react/admin/landmark/update', { method: 'POST' })
      const data = await res.json()
      setSyncLog(data)
      showToast('전체 업데이트 완료!')  // ← 여기
    } catch (e) {
      showToast('업데이트 중 오류가 발생했어요.', 'err')  // ← 여기
    } finally {
      setBusy(null)
    }
  }


  const collectOverview = async () => {
    if (busy) return
    setBusy('overview')
    try {
      const res = await fetch('/react/admin/landmark/collect-overview', { method: 'POST' })
      const data = await res.json()
      setSyncLog(data)
      showToast('개요 수집 완료!')
    } catch (e) {
      showToast('개요 수집 중 오류가 발생했어요.', 'err')
    } finally {
      setBusy(null)
    }
  }

  return (
    <main className="mx-auto my-10 max-w-6xl px-6">

      {/* 페이지 헤더 */}
      <div className="mb-7 flex items-end justify-between gap-4">
        <div>
          <div className="mb-1.5 flex items-center gap-2 whitespace-nowrap text-[13px] font-semibold text-cobalt">
            <Icon.pin width="15" height="15" />
            한국관광공사 API
          </div>
          <h1 className="text-[28px] font-extrabold tracking-[-0.03em] text-ink">관광지 관리</h1>
          <p className="mt-1 text-sm text-muted">API 데이터 수집 및 업데이트</p>
        </div>
      </div>

      {/* 상태 카드 */}
      <div className="mb-5 grid grid-cols-4 gap-4">
        <StatCard
          label="수집된 관광지"
          value={syncLog?.count ? syncLog.count.toLocaleString() : null}
          caption="총 등록 수"
          accent
        />
        <StatCard
          label="마지막 수집"
          value={syncLog?.collectSync ? fmt(new Date(syncLog.collectSync)) : null}
          caption={`${page} / ${totalPages} 페이지까지 완료`}
        />
        <StatCard
          label="마지막 업데이트"
          value={syncLog?.updateSync ? fmt(new Date(syncLog.updateSync)) : null}
          caption="전체 갱신 기준"
        />
        <StatCard
          label="개요 수집"
          value={syncLog?.overviewCount ? syncLog.overviewCount.toLocaleString() : null}
          caption={`${syncLog?.overviewCount ?? 0} / ${syncLog?.count ?? 0} 건`}
        />
      </div>

      {/* 수집 패널 */}
      <div className="rounded-xl border border-line bg-white p-6 shadow-card">
        <h2 className="text-[17px] font-bold text-ink">데이터 수집</h2>
        <p className="mt-1 max-w-2xl text-sm leading-relaxed text-muted">
          API 호출 제한(하루 {DAILY_LIMIT.toLocaleString()}회)으로 인해 끊어서 수집해요.
        </p>

        {/* 오늘 호출 사용량 */}
        <div className="mt-6 rounded-lg border border-line bg-sunk/60 px-4 py-3">
          <div className="flex items-center justify-between text-[13px]">
            <span className="font-semibold text-muted">오늘 호출 사용량</span>
            <span className="tabular-nums text-faint">
              <span className={calls >= DAILY_LIMIT ? 'font-bold text-cobalt' : 'font-semibold text-ink'}>{calls.toLocaleString()}</span>
              {' / '}{DAILY_LIMIT.toLocaleString()}회
            </span>
          </div>
          <div className="mt-2 h-1.5 w-full overflow-hidden rounded-full bg-line">
            <div className="h-full rounded-full bg-cobalt/40 transition-all duration-300" style={{ width: `${callPct}%` }} />
          </div>
        </div>

        {/* 진행 상황 */}
        <div className="mt-5">
          <div className="mb-2 flex items-center justify-between text-sm">
            <span className="font-semibold text-muted">수집 진행 상황</span>
            <span className="tabular-nums text-faint">
              <span className="font-semibold text-ink">{page.toLocaleString()}</span> / {totalPages.toLocaleString()} 페이지
              <span className="ml-2">({pct}%)</span>
            </span>
          </div>
          <div className="h-2.5 w-full overflow-hidden rounded-full bg-line">
            <div className="h-full rounded-full bg-cobalt transition-[width] duration-150 ease-out" style={{ width: `${pct}%` }} />
          </div>
        </div>

        {/* 버튼 */}
        <div className="mt-6 flex items-center gap-3">
          {!done && (
            <button
              onClick={collect}
              disabled={busy}
              className="inline-flex h-11 items-center gap-2 rounded-lg bg-cobalt px-6 text-sm font-semibold text-white shadow-card transition hover:-translate-y-px hover:bg-cobalt-ink disabled:cursor-not-allowed disabled:opacity-50 disabled:hover:translate-y-0"
            >
              {busy === 'collect'
                ? <><Icon.refresh className="animate-spin" width="17" height="17" /> 수집 중…</>
                : <><Icon.download width="17" height="17" /> 이어서 수집</>}
            </button>
          )}

          <button
            onClick={collectOverview}
            disabled={busy}
            className="inline-flex h-11 items-center gap-2 rounded-lg border border-line bg-white px-6 text-sm font-semibold text-ink transition hover:border-faint hover:bg-sunk disabled:cursor-not-allowed disabled:opacity-50"
          >
            {busy === 'overview'
              ? <><Icon.refresh className="animate-spin" width="17" height="17" /> 개요 수집 중…</>
              : <><Icon.download width="17" height="17" /> 개요 수집</>}
          </button>

          <button
            onClick={update}
            disabled={busy}
            className="inline-flex h-11 items-center gap-2 rounded-lg border border-line bg-white px-6 text-sm font-semibold text-ink transition hover:border-faint hover:bg-sunk disabled:cursor-not-allowed disabled:opacity-50"
          >
            {busy === 'update'
              ? <><Icon.refresh className="animate-spin" width="17" height="17" /> 갱신 중…</>
              : <><Icon.refresh width="17" height="17" /> 전체 업데이트</>}
          </button>
        </div>
      </div>

      {toast && (
        <div className={`fixed bottom-6 right-6 rounded-lg px-5 py-3 text-sm font-semibold text-white shadow-lg transition ${toast.type === 'err' ? 'bg-err' : 'bg-ok'}`}>
          {toast.msg}
        </div>
      )}
    </main>
  )
}
