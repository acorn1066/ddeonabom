// src/components/AdminModal.jsx

const AdminModal = ({ title, onClose, children, footer }) => {
    return (
        <>
            <div
                className="fixed inset-0 z-50 flex items-center justify-center bg-black/50"
                onClick={onClose}
            >
                <div
                    className="w-full max-w-lg rounded-2xl bg-white shadow-xl"
                    onClick={(e) => e.stopPropagation()}
                >
                    {/* 헤더 */}
                    <div className="flex items-center justify-between border-b p-4">
                        <h1 className="text-lg font-bold">{title}</h1>
                        <button onClick={onClose} className="text-gray-400 hover:text-gray-600">✕</button>
                    </div>

                    {/* 본문 */}
                    <div className="max-h-96 overflow-y-auto p-4">
                        {children}
                    </div>

                    {/* 푸터 */}
                    {footer && (
                        <div className="flex justify-end gap-2 border-t p-4">
                            {footer}
                        </div>
                    )}
                </div>
            </div>
            <div className="modal-backdrop fade show"></div>
        </>
    );
};

export default AdminModal;